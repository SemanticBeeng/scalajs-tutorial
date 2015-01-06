package business

import business.domain.{BusinessException, Customer, CustomerValidator}
import scala.scalajs.js
import utest._

object CustomerValidatorTest extends TestSuite {

  val tests = TestSuite {
    'square {
      // Shared tests for the shared stuff in `Demo`
      * - assert(1 == 1)
    }
    //    'evalJS{
    //      // Tests for stuff in `DemoPlatform`. Note that even though the
    //      // `evalJS` is defined differently in both JS (which uses the
    //      // native `eval` function) and JVM (which uses Rhino) we can run
    //      // the same tests on both implementations
    //      * - assert(DemoPlatform.evalJS("1 + 2") == "3")
    //      * - assert(
    //        DemoPlatform.evalJS("(function(x){return x + 5})(10)") == "15"
    //      )
    //    }

    'customerValidation {
      val businessExceptions: List[BusinessException] = CustomerValidator.validate(Customer(-1, "Joe", "Wolf", Nil))

      * - assert(businessExceptions.length == 2)
    }
  }
}