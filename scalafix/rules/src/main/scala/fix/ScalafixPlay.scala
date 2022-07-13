package fix

import scalafix.v1._
import scala.meta._

class ScalafixPlay extends SemanticRule("ScalafixPlay") {

  override def fix(implicit doc: SemanticDocument): Patch = {
    (
      replaceImport("play/api/mvc/Controller#", importer"play.api.mvc.{BaseController, ControllerComponents}") ::
      doc.tree.collect {
        case c @ Defn.Class(_, _, _, Ctor.Primary(_, _, _), Template(_, inits, _, _)) =>
          inits.collect {
            case init @ Init(tpe, _, _) if tpe.symbol.value == "play/api/mvc/Controller#" =>
              List(
                Patch.replaceTree(init, "BaseController"),
                addClassParameter(c, "val controllerComponents: ControllerComponents")
              )
          }.flatten
      }.flatten
    ).asPatch
  }

  def replaceImport(from: String, to: Importer)(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case controller: Importee if controller.symbol.value == from =>
        List(
          Patch.removeImportee(controller),
          Patch.addGlobalImport(to)
        )
    }.flatten.asPatch
  }

  def addClassParameter(c: Defn.Class, param: String): Patch = {
    if (c.ctor.paramss.exists(_.nonEmpty)) {
      Patch.addRight(c.ctor.children.last, s", $param")
    } else if (c.ctor.paramss.nonEmpty) {
      Patch.replaceTree(c.ctor, s"($param)")
    } else {
      Patch.addRight(c.children.head, s"($param)")
    }
  }
}
