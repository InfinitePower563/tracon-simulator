package skill.issue.traconsim.sim.objects;

import org.joml.Vector2d;
import org.joml.Vector2f;
import skill.issue.traconsim.Main;
import skill.issue.traconsim.Renderer;
import skill.issue.traconsim.sim.utils.DBStatus;

public class DataBlock {
    public String callsign;
    public int altitude;
    public double heading;
    public int speed;
    public double x;
    public double y;
    public int assignedAltitude;
    public double assignedHeading;
    public int assignedSpeed;
    public Owner owner = Owner.NONE;
    public Owner handoffTarget = Owner.NONE;
    public Owner pointoutTarget = Owner.NONE;
    public DBStatus status = DBStatus.UNOWNED;
    public boolean highlighted = false;
    public long hoTicks = 0;
    public BoundingBox4d bb;

    public String destination = "KIAH";
    public String type = "B737";

    public DataBlock(String callsign, int altitude, double heading, int speed, double x, double y) {
        this.callsign = callsign;
        this.altitude = altitude;
        this.heading = heading;
        this.speed = speed;
        this.x = x;
        this.y = y;

        assignedAltitude = altitude;
        assignedHeading = heading;
        assignedSpeed = speed;

        bb = new BoundingBox4d(
                new Vector2d(-Renderer.DB_SIZE/2, -Renderer.DB_SIZE/2),
                new Vector2d(Renderer.DB_SIZE/2, -Renderer.DB_SIZE/2),
                new Vector2d(Renderer.DB_SIZE/2, Renderer.DB_SIZE/2),
                new Vector2d(-Renderer.DB_SIZE/2, Renderer.DB_SIZE/2)
        );
    }

    public void setHeading(double heading) {
        this.assignedHeading = heading;
    }
    public void setAltitude(int altitude) {
        this.assignedAltitude = altitude;
    }
    public void setSpeed(int speed) {
        this.assignedSpeed = speed;
    }
    public void update() {
        heading += (assignedHeading - heading) / 50;
        if (assignedHeading-heading < 2) heading = assignedHeading;

        altitude += (assignedAltitude - altitude) / 50;
        if (assignedAltitude-altitude < 2) altitude = assignedAltitude;

        speed += (assignedSpeed - speed) / 50;
        if (assignedSpeed-speed < 2) speed = assignedSpeed;

        x += speed * Math.sin(Math.toRadians(heading)) * 0.0001;
        y += speed * Math.cos(Math.toRadians(heading)) * 0.0001;

        if (owner == Position.CURRENT.pos()) {
            status = DBStatus.OWNED;
        }
        if (status == DBStatus.HO) {
            hoTicks++;
        } else hoTicks = 0;
    }
}
