package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Control.Colour;
import static org.firstinspires.ftc.teamcode.Control.Distance;

import java.util.Arrays;

/**
 * Manages the robot's drive.
 */
public class Drive {
    private Hardware hardware;
    private OpMode opMode;
    private Layout layout;
    private Limelight limelight;

    // private Follower follower;

    private int swapFront = -1;
    private double drivePower = 1;
    private double autoStartTime = -1;

    /**
     * Declares OpMode, hardware, and layout, and sets the starting pose, updates it, and starts
     * TeleOp.
     *
     * @param opMode   the OpMode object
     * @param hardware the hardware object
     * @param layout   the controller layout object
     */
    public Drive(OpMode opMode, Hardware hardware, Layout layout, Limelight limelight) {
        this.opMode = opMode;
        this.hardware = hardware;
        this.layout = layout;
        this.limelight = limelight;

        // follower = Constants.createFollower(opMode.hardwareMap);
        // // TODO: set it with limelight if possible, else default)
        // // just loop in int, trying to find it, and if not found,
        // // set it to new here?
        // // or have them set the starting position manually if limelight can't
        // // find it
        // // (and also let them disable limit switches, etc.)
        // // TODO: prev.
        // follower.setStartingPose(new Pose());
        // follower.update();
        // follower.startTeleopDrive();
    }

    /**
    * Updates the follower
     */
    public void update() {
        // follower.update();
    }

    public void tel(Telemetry telemetry) {
        telemetry.addData("Left front power", hardware.leftFrontDriveMotor.getPower());
        telemetry.addData("Left rear power", hardware.leftRearDriveMotor.getPower());
        telemetry.addData("Right front power", hardware.rightFrontDriveMotor.getPower());
        telemetry.addData("Right rear power", hardware.rightRearDriveMotor.getPower());
    }

    /**
     * Stops the robot
     */
    public void stopRobot() {
        autoStartTime = -1;
        setPower(0, 0, 0, 0);
    }

    private boolean aimed = false;

    private double dx;
    private double dz;

    // TODO: write initial procedure for this

    public boolean aimTarget() {

        if (!limelight.foundTarget()) {
            return false;
        }

        // if (Math.abs(limelight.targetDx()))

        double x = 0;
        double y = 0;
        double yaw = 0;

        // if (limelight.targetDx() > 0) {
        //     y = -.3;
        // } else {
        //     y = .3;
        // }

        double theta = limelight.angle_from_target();

        // if (theta > 0) {
        //     x = Math.cos((theta * Math.PI / 180));
        //     y = Math.sin((theta * Math.PI / 180));
        //     // yaw = .3;
        // } else {
        //     // yaw = -.3;
        // }
        x = .3 * Math.cos((theta * Math.PI / 180));
        y = .3 * Math.sin((theta * Math.PI / 180));

        opMode.telemetry.addData("theta: ", theta);
        opMode.telemetry.addData("theta rad: ", theta * Math.PI / 180);
        opMode.telemetry.addData("x power: ", x);
        opMode.telemetry.addData("y power: ", y);

        // x is forward/backward, y is left/right
        moveRobot(x, y, yaw);

        return false;

        // opMode.telemetry.addLine("aiming");
        // if (aimed) {
        //     return true;
        // }

        // // get tx from limelight, 10 means tilt with .15(?), |tx|<5 means
        // // we're on target
        // if (!limelight.foundTarget()) {
        //     opMode.telemetry.addLine("couldn't find target, turning until found");
        //     moveRobot(0, 0, -.15 * (red ? -1 : 1));
        //     aimed = false;
        // } else {
        //     opMode.telemetry.addLine("found target");
        //     double angle = limelight.angle_from_target();
        //     if (angle > 2) {
        //         opMode.telemetry.addLine("moving right");
        //         moveRobot(0, 0, .15);
        //         aimed = false;
        //     } else if (angle < -2) {
        //         opMode.telemetry.addLine("moving left");
        //         moveRobot(0, 0, -.15);
        //         aimed = false;
        //     } else {
        //         opMode.telemetry.addLine("aimed at target");
        //         aimed = true;
        //     }
        // }

        // return aimed;
    }

    /**
     * Sets TeleOP drive
     */
    public void gamepadDrive() {
        // currently deprecated, but leaving since making a double sided
        // control scheme is very likely
        // if (layout.frontFront()) {
        //     swapFront = 1;
        // } else if (layout.frontBack()) {
        //     swapFront = -1;
        // }

        // TODO: move drivepower and swapfront to moveRobot

        // TODO: test robot vs field centric
        // follower.setTeleOpDrive(layout.driveForwardAmount() * swapFront * drivePower,
        //   layout.driveStrafeAmount() * swapFront * drivePower, layout.driveYawAmount() * drivePower, true);

        if (layout.tmpAim()) {
            aimTarget();
        } else {
        moveRobot(layout.driveForwardAmount(),
                layout.driveStrafeAmount(),
                -layout.driveYawAmount());
        }
    }

    /**
     * Executes autonomous drive routine based on distance and alliance colour:
     * - CLOSE (RED / BLUE): Move left for 1.5 seconds.
     * - FAR (RED / BLUE): Drive forward for 5 seconds.
     *
     * So far all it does is park.
     *
     * @param distance CLOSE or FAR
     * @param colour RED or BLUE
     */
    public void runAuto(Distance distance, Colour colour) {
        if (autoStartTime == -1) {
            autoStartTime = opMode.time;
        }

        double elapsedTime = opMode.time - autoStartTime;

        switch (colour) {
            case RED:
                switch (distance) {
                    case CLOSE:
                        if (elapsedTime < 1.5) {
                            moveRobot(0, 0.5, 0);
                        } else {
                            stopRobot();
                        }
                        break;

                    case FAR:
                        if (elapsedTime < 5.0) {
                            moveRobot(0.5, 0, 0);
                        } else {
                            stopRobot();
                        }
                        break;
                }
                break;

            case BLUE:
                switch (distance) {
                    case CLOSE:
                        if (elapsedTime < 1.5) {
                            moveRobot(0, 0.5, 0);
                        } else {
                            stopRobot();
                        }
                        break;

                    case FAR:
                        if (elapsedTime < 5.0) {
                            moveRobot(0.5, 0, 0);
                        } else {
                            stopRobot();
                        }
                        break;
                }
                break;
        }
    }

    /**
     * Moves the robot based on directional inputs.
     *
     * @param x   x-value, positive is forward
     * @param y   y-value, positive is strafe left
     * @param yaw yaw, positive is clockwise
     */
    private void moveRobot(double x, double y, double yaw) {
        // add reverse parameter? and encapsulate that here?
        // or add another method, moveRobotDirectional, which also
        // takes a reverse parameter, and calls this

        // if (aiming) {
        //     if (Math.abs(x + y + yaw) > .3) {
        //         opMode.telemetry.addLine("not aiming");
        //         aiming = false;
        //     } else {
        //         opMode.telemetry.addLine("aiming");
        //         return;
        //     }
        // }

        x *= swapFront * drivePower;
        y *= swapFront * drivePower;
        yaw *= drivePower;

        double leftFrontPower = x - y + yaw;
        double leftRearPower = x + y + yaw;
        double rightFrontPower = x + y - yaw;
        double rightRearPower = x - y - yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Arrays.stream(
                        new double[]{leftFrontPower, leftRearPower, rightFrontPower, rightRearPower})
                .map(Math::abs)
                .max()
                .getAsDouble();

        if (max > 1.0) {
            Arrays.stream(new Double[]{leftFrontPower, leftRearPower, rightFrontPower, rightRearPower}).map(n -> n /= max);
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftRearPower /= max;
            rightRearPower /= max;
        }

        setPower(leftFrontPower, leftRearPower, rightFrontPower, rightRearPower);
    }

    /**
     * Controls the power each wheel has.
     *
     * @param leftFront  the left front wheel DCMotor object
     * @param leftRear   the left rear wheel DCMotor object
     * @param rightFront the right front wheel DCMotor object
     * @param rightRear  the right rear wheel DCMotor object
     */
    private void setPower(double leftFront, double leftRear, double rightFront, double rightRear) {
        hardware.leftFrontDriveMotor.setPower(leftFront);
        hardware.leftRearDriveMotor.setPower(leftRear);
        hardware.rightFrontDriveMotor.setPower(rightFront);
        hardware.rightRearDriveMotor.setPower(rightRear);
    }
}
