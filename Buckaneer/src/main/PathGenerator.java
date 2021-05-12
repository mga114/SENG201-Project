package main;

public class PathGenerator {
	// 0: 170, 450
	// 1: 435, 330
	// 2: 820, 240
	// 3: 910, 570
	// 4: 390, 510
	private Path path01;
	private Path path02;
	private Path path03;
	private Path path04;
	private Path path12;
	private Path path13;
	private Path path14;
	private Path path23;
	private Path path24;
	private Path path34;
	
	private int xPoints[];
	private int yPoints[];
	private Path pathMap[][];
	public Path[][] pathPoints() {
		pathMap = new Path[5][5];
		xPoints = new int[5];
		yPoints = new int[5];
		xPoints[0] = 170;
		yPoints[0] = 450;
		xPoints[1] = 170;
		yPoints[1] = 190;
		xPoints[2] = 380;
		yPoints[2] = 190;
		xPoints[3] = 380;
		yPoints[3] = 330;
		xPoints[4] = 435;
		yPoints[4] = 330;
		path01 = new Path(xPoints, yPoints, "path01");
		pathMap[0][1] = path01;
		pathMap[1][0] = path01;
		
		return pathMap;
	}
}
