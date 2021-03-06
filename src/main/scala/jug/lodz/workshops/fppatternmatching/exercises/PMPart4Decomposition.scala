package jug.lodz.workshops.fppatternmatching.exercises

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

import jug.lodz.workshops.Workshops
import jug.lodz.workshops.Workshops.check

import scala.collection.mutable.ListBuffer

/**
  * Created by pawel on 24.04.16.
  */
object PMPart4Decomposition {

  def main(args: Array[String]) {
    Demonstration.demo()

    //    println("[------EXERCISES------]")
    //    println("\n[------EXERCISE LEVEL 1------]")
    //    ExerciseLevel1.exercise11()
    //    ExerciseLevel1.exercise12()
    //
    //    println("\n[------EXERCISE LEVEL 2------]")
    //    ExerciseLevel2.exercise21()
  }

  object Demonstration {
    def demo() = {
      println("***[DEMONSTRATION]***")
      println("* EXAMPLE1 : SIMPLE CLASS")

      class Wrapper(val value: String)
      //      new Wrapper("example1") match { case Wrapper(value) => println(value)}

      println("* EXAMPLE2 : COMPANION OBJECT COMPOSITION")
      class Wrapper2 private(val value: String)
      object Wrapper2 {
        def apply(s: String) = new Wrapper2(s)
      }

      //      Wrapper2("example2") match { case Wrapper2(value) => println(value)}  // cannot resolve Wrapper2.unapply

      println("* EXAMPLE3 : COMPANION OBJECT DECOMPOSITION")
      class Wrapper3 private(val value: String)
      object Wrapper3 {
        def apply(s: String) = new Wrapper3(s)
        def unapply(w3: Wrapper3): Option[String] = Some(w3.value)
      }
// KATA
//      Wrapper3("  --> example3 is working!") match {
//        case Wrapper3(value) => println(value)
//      }

      println("\n* EXAMPLE4 : STATE ENCAPSULATION")
      class ObjectWithState() {
        private val history: ListBuffer[String] = ListBuffer()
        def act(message: String): Unit = history.prepend(message)
      }

      object ObjectWithState {
        def unapply(state: ObjectWithState): Option[(String, Int)] =
          state.history.headOption.map(lastMessage => (lastMessage, state.history.size))
      }
// KATA
//      val state = new ObjectWithState()
//      state.act("message1")
//      state.act("message2")
//      state.act("message3")
//
//      state match {
//        case ObjectWithState(lastMessage, allMessagesSize) =>
//          println(s"  --> last message is : $lastMessage and there are $allMessagesSize messages")
//      }
//
//      val state2 = new ObjectWithState()
//      state2 match {
//        case ObjectWithState(lastMessage, allMessagesSize) =>
//          println(s"  -->last message is : $lastMessage and there are $allMessagesSize messages")
//        case _ => println("  --> state2 is empty")
//      }
    }
  }

  object ExerciseLevel1 {

    class SimpleClass(val parameter: Int)

    // extract parameter from simple class
    object SimpleClass {
      def unapply(sc: SimpleClass): Option[Int] = ???
    }

    def exercise11() = {
      new SimpleClass(20) match {
        case SimpleClass(value) => check("EXERCISE11")(value , 20)
      }
    }

    // implement Fraction-to-string with PM
    class Fraction(val c:Int, val d:Int)
    object Fraction{
      def unapply(f:Fraction):Option[(Int,Int)] = ???
    }

    def matchFraction(f:Fraction):String=f match {  // if d==1 then just return s"$c"
      case Fraction(c,d) => ???
      case _ => ???
    }

    def exercise12()={
        val check12=check("EXERCISE12") _
        check12(matchFraction(new Fraction(5,1)),"5")
        check12(matchFraction(new Fraction(-5,1)),"-5")
        check12(matchFraction(new Fraction(5,2)),"5/2")
        check12(matchFraction(new Fraction(-5,2)),"-5/2")
    }
  }

  /**
    * x - encapsulate state
    * x - CAS and multithreading
    */
  object ExerciseLevel2 {
    class Counter{
      private val state:AtomicInteger=new AtomicInteger()

      def increment:Int = ???   // increment counter in thread safe context
    }

    object Counter{
      def unapply(counter:Counter):Option[Int] = ???
    }

    val globalCounter=new Counter()

    def exercise21()={
      (1 to 5).map(_ => new Thread {
        override def run() = (1 to 10).foreach(_ => globalCounter.increment)
      }).foreach(_.start())

      TimeUnit.MILLISECONDS.sleep(100)

      globalCounter match {
        case Counter(value) => check("EXERCISE21")(value,50)
      }
    }
  }



}
