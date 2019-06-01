package fi.gemwars.log;

import java.io.PrintStream;

import org.newdawn.slick.util.DefaultLogSystem;
import org.newdawn.slick.util.LogSystem;

public class GemwarLogSystem extends DefaultLogSystem implements LogSystem {

	public GemwarLogSystem(PrintStream output) {
		out = output;
	}
}
