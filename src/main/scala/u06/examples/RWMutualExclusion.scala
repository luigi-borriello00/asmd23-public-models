package pc.examples

export u06.modelling.PetriNet
import u06.utils.MSet

object RWMutualExclusion:

  enum Place:
    case START, CHOICE, WANT_READING, WANT_WRITE, JOLLY, READING, WRITING

  export Place.*
  export u06.modelling.PetriNet.*
  export u06.modelling.SystemAnalysis.*
  export u06.utils.MSet

  // DSL-like specification of a Petri Net
  def readersAndWritersPetriNet = PetriNet[Place](
    MSet(START) ~~> MSet(CHOICE),
    MSet(CHOICE) ~~> MSet(WANT_READING),
    MSet(CHOICE) ~~> MSet(WANT_WRITE),
    MSet(WANT_READING, JOLLY) ~~> MSet(JOLLY, READING),
    MSet(READING) ~~> MSet(START),
    MSet(WANT_WRITE, JOLLY) ~~> MSet(WRITING) ^^^ MSet(READING),
    MSet(WRITING) ~~> MSet(START, JOLLY)
  ).toSystem


@main def mainRWMutualExclusion =
  import RWMutualExclusion.*
  // example usage
  println(readersAndWritersPetriNet.paths(MSet(START, START, JOLLY), 10).toList.mkString("\n"))

