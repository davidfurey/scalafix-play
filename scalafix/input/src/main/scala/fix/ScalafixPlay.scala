/*
rule = ScalafixPlay
*/
package fix

import play.api.mvc.{ Controller, Cookie, Results }

object ScalafixPlay {
  class ExampleController(val x: Int) extends Results with Controller {

  }

  class OtherController() extends Controller {

  }

  class FinalController extends Controller {

  }

  class KeepThisAsIs(val y: Int) extends Results
}

class OuterExampleController(val x: Int) extends Results with Controller {

}
