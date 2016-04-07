import java.io.File

import akka.actor.{Props, ActorSystem}
import com.mildlyskilled.{Scene, Image, Trace}
import main.scala.com.mildlyskilled.Actors.{Pixelate, ActorCoordinator}

object Tracer extends App {

  val PATH = "home/annabel/Documents/SDP/CWThree/sdp2016-ray-tracer/src/main/resources/"
  val fileName = "input.dat"
  val (infile, outfile) = (PATH + fileName, "output.png")
  val scene = Scene.fromFile(infile)
  val t = new Trace
  render(scene, outfile, t.Width, t.Height)

  println("rays cast " + t.rayCount)
  println("rays hit " + t.hitCount)
  println("light " + t.lightCount)
  println("dark " + t.darkCount)

  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    val image = new Image(width, height)

    // Init the coordinator -- must be done before starting it.
     // Coordinator.init(image, outfile)

    // TODO: Start the Coordinator actor.
    // started!
    val system = ActorSystem("PixelSystem")

    val coordinator = system.actorOf(Props(new ActorCoordinator(outfile, image)),
      name = "master")

    coordinator ! Pixelate

      //scene.traceImage(width, height)

    // TODO:
    // This one is tricky--we can't simply send a message here to print
    // the image, since the actors started by traceImage haven't necessarily
    // finished yet.  Maybe print should be called elsewhere?
    //Coordinator.print
    //called in Coordinator Actor
  }

}
