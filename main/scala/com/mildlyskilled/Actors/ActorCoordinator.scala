package main.scala.com.mildlyskilled.Actors


import akka.actor.{Props, ActorLogging, Actor}
import akka.routing.RoundRobinPool
import com.mildlyskilled.{Colour, Image}


/**
  * Created by annabel on 05/04/16.
  */
class ActorCoordinator (outputFile: String, ima: Image) extends Actor with ActorLogging {
  var image = ima
  var outfile = outputFile
  var pixelLine = ima.height
  val pixelWide = ima.width
  //var waiting = ima.width * ima.height

  val tracerRouter = context.actorOf(
    Props[Scene].withRouter(RoundRobinPool(pixelLine)), name = "tracerRouter")

  def receive = {
    case Pixelate ⇒
      for (i ← 0 until pixelLine) tracerRouter ! Tracer(pixelWide, pixelLine)

    case set(x: Int, y: Int, c: Colour) =>
      image(x, y) = c
      pixelLine -= 1
      if(pixelLine == 0){
        self ! draw(image, outfile) }

    case draw(image, outfile) =>
      assert(pixelLine == 0)
      image.print(outfile)
      context stop self

    //case Finished(value) ⇒
    //  pi += value
    //  nrOfResults += 1
    //  if (nrOfResults == nrOfMessages) {
     //   // Send the result to the listener
    //    listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start) millis)
    //    // Stops this actor and all its supervised children
    //    context stop self
  }
}
