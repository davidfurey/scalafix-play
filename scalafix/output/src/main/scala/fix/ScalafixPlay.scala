package fix

import play.api.mvc.{ Cookie, Results }
import play.api.mvc.{ BaseController, ControllerComponents }

object ScalafixPlay {
  class ExampleController(val x: Int, val controllerComponents: ControllerComponents) extends Results with BaseController {

  }

  class OtherController(val controllerComponents: ControllerComponents) extends BaseController {

  }

  class FinalController(val controllerComponents: ControllerComponents) extends BaseController {

  }

  class KeepThisAsIs(val y: Int) extends Results
}

class OuterExampleController(val x: Int, val controllerComponents: ControllerComponents) extends Results with BaseController {

}
