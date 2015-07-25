package sangria.validation.rules

import sangria.ast
import sangria.ast.AstVisitorCommand._
import sangria.renderer.{QueryRenderer, SchemaRenderer}
import sangria.validation._

/**
 * Known argument names
 *
 * A GraphQL field is only valid if all supplied arguments are defined by
 * that field.
 */
class KnownArgumentNames extends ValidationRule {
  override def visitor(ctx: ValidationContext) = new AstValidatingVisitor {
    override val onEnter: ValidationVisit = {
      case ast.Argument(name, _, pos) =>
        ctx.typeInfo.fieldDef match {
          case Some(field) if !field.arguments.exists(_.name == name) =>
            Left(UnknownArgViolation(
              name,
              field.name,
              ctx.typeInfo.previousParentType.fold("")(SchemaRenderer.renderTypeName(_, topLevel = true)),
              ctx.sourceMapper,
              pos))
          case _ =>
            Right(Continue)
        }
    }
  }
}