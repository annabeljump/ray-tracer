package main.scala.com.mildlyskilled.Actors

import com.mildlyskilled.{Image, Colour}

/**
  * Created by annabel on 06/04/16.
  */
sealed trait pixelMessage
case object Pixelate extends pixelMessage
case class Tracer(i: Int, j: Int) extends pixelMessage
case class set(x: Int, y: Int, c: Colour) extends pixelMessage

case class draw(image: Image, out: String)
