package jug.lodz.workshops.propertybased.exercises

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 06.03.16.
  */
class PBTFunctorSpec extends PropSpec with Matchers with PropertyChecks{

  val intTointFunctions:Gen[Int=>Int]=Gen.oneOf((x:Int)=>x+1,(x:Int)=>x*2,(x:Int)=>x%10)
  val stringToIntFunctions:Gen[String=>Int]=Gen.oneOf((s:String)=>s.toInt,(s:String)=>s.length)

  val intAsText: Gen[String] =Gen.choose(0,Int.MaxValue).map(_.toString)

  val notEmptyList= Gen.listOf(intAsText).suchThat(_.size > 0)

  property("List satisfies Functor identity property"){
    forAll(notEmptyList){ ls=>
      ls.map(identity) shouldBe(identity(ls))
    }
  }

  property("List satisfies Functor associativity property"){
    forAll(notEmptyList,stringToIntFunctions,intTointFunctions){ (ls,fsi,fii)=>
      ls.map(fsi andThen fii) shouldBe(ls.map(fsi).map(fii))
    }
  }

  //EXERCISES
  property("Option satisfies Functor identity property"){

  }

  property("Option satisfies Functor associativity property"){

  }


}
