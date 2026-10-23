package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import static org.firstinspires.ftc.teamcode.Control.Colour;

public class Limelight {
    private final OpMode opMode;
    private final Hardware hardware;
    private final TelemetryManager telemetryM;
    private LLResult validResult;
    private LLResultTypes.FiducialResult target;

    public Limelight(OpMode opMode, Hardware hardware, TelemetryManager telemetryM) {
        this.opMode = opMode;
        this.hardware = hardware;
        this.telemetryM = telemetryM;
        hardware.limelight.pipelineSwitch(0);
        hardware.limelight.start();
    }

    public void update(Colour colour) {
        LLResult result = hardware.limelight.getLatestResult();

        // Clear old readings so a lost target doesn't stay on the screen.
        validResult = null;
        target = null;
        if (result == null || !result.isValid()) {
            return;
        }

        validResult = result;

        // telemetryM.debug(
        if (result.isValid()) {
            opMode.telemetry.addLine("limelight found valid result");
            // telemetryM.debug("pose", result.getBotpose().toString());
            Pose3D pose = result.getBotpose();
            Position pos = pose.getPosition();
            // telemetryM.debug("pose", pose.toString());
            // telemetryM.debug("theoretical distance from goal", Math.sqrt(Math.pow(pos.x, 2) + Math.pow(pos.y, 2)));
            // telemetryM.debug("tx", result.getTx());
            // telemetryM.debug("txnc", result.getTxNC());
            // telemetryM.debug("ty", result.getTy());
            // telemetryM.debug("tync", result.getTyNC());

            // target = null;
            for (int i = 0; i < result.getFiducialResults().size(); ++i) {
                LLResultTypes.FiducialResult target = result.getFiducialResults().get(i);
                // red 24, blue 20
                // opMode.telemetry.addData("limelight colour: ", colour);
                // opMode.telemetry.addData("found id: ", target.getFiducialId());
                telemetryM.debug("");
                telemetryM.debug("limelight colour: ", colour);
                telemetryM.debug("found id: ", target.getFiducialId());
                // TODO: rewrite to expect any of four apriltags
                // int id = colour == Colour.RED ? 24 : 20;
                // if (target.getFiducialId() != id) {
                //     // telemetryM.debug("found non-target apriltag");
                //     continue;
                // }
                if (target.getFiducialId() < this.target.getFiducialId()) {
                this.target = target;
                }
                opMode.telemetry.addLine("found apriltag");
                telemetryM.debug("skew: ", target.getSkew());
                telemetryM.debug("cameraPoseTargetSpace: ", target.getCameraPoseTargetSpace());
                telemetryM.debug("robotPoseTargetSpace: ", target.getRobotPoseTargetSpace());
                telemetryM.debug("targetPoseCameraSpace: ", target.getTargetPoseCameraSpace());
                telemetryM.debug("targetPoseRobotSpace: ", target.getTargetPoseRobotSpace());
                telemetryM.debug("targetXDegrees: ", target.getTargetXDegrees());
                telemetryM.debug("targetYDegrees: ", target.getTargetYDegrees());
                telemetryM.debug("targetXDegreesNoCrosshair: ", target.getTargetXDegreesNoCrosshair());
                telemetryM.debug("targetYDegreesNoCrosshair: ", target.getTargetYDegreesNoCrosshair());
            }
        }
    }

    public void tel() {
        opMode.telemetry.addData("Limelight result valid", resultValid());
        opMode.telemetry.addData("Limelight target found", foundTarget());
        if (validResult == null) {
            return;
        }

        Pose3D pose = validResult.getBotpose();
        if (pose != null) {
            telemetryM.debug("yaw", pose.getOrientation().getYaw());
            telemetryM.debug("pitch", pose.getOrientation().getPitch());
            telemetryM.debug("roll", pose.getOrientation().getRoll());
        }
        telemetryM.debug("Limelight centered", Math.abs(validResult.getTx()) < 4);

        if (target != null) {
            opMode.telemetry.addData("AprilTag ID", target.getFiducialId());
            opMode.telemetry.addData("Target angle (degrees)", target.getTargetXDegrees());
            telemetryM.debug("skew", target.getSkew());
            telemetryM.debug("cameraPoseTargetSpace", target.getCameraPoseTargetSpace());
            telemetryM.debug("robotPoseTargetSpace", target.getRobotPoseTargetSpace());
            telemetryM.debug("targetPoseCameraSpace", target.getTargetPoseCameraSpace());
            telemetryM.debug("targetPoseRobotSpace", target.getTargetPoseRobotSpace());
            telemetryM.debug("targetXDegrees", target.getTargetXDegrees());
            telemetryM.debug("targetYDegrees", target.getTargetYDegrees());
            telemetryM.debug("targetXDegreesNoCrosshair", target.getTargetXDegreesNoCrosshair());
            telemetryM.debug("targetYDegreesNoCrosshair", target.getTargetYDegreesNoCrosshair());
        //     // camposetargspace z .75-2ish for shooting

        //     if (Math.abs(result.getTx()) < 4) {
        //         telemetryM.debug("can shoot");

        //         // if we're close, use one range
        //         // if we're far, use another

        //         // TODO: get the led working
        //         // hardwhare.canShootLed.setState(false);
        //         // hardware.canShootLedServo.setPower(0);
        //         // hardware.canShootLedServo.setPosition(1.0);
        //     } else {
        //         telemetryM.debug("can't shoot");
        //         // hardware.canShootLed.setState(true);
        //         // hardware.canShootLED.enable(false);
        //         // hardware.canShootLedServo.setPower(0);
        //         // hardware.canShootLedServo.setPosition(0.5);
        //     }
        // } else {
        //     opMode.telemetry.addLine("no pose");
        }
    }

    public double angle_from_target() {
        return target.getTargetXDegrees();
    }

    // NOTE: x is left/right from the target, z is back/foraward,
    // y is up and down, for some reason

    // x degrees is what we want, y degrees is up/down

    public double targetDx() {
        return target.getCameraPoseTargetSpace().getPosition().x;
    }

    public double targetDz() {
        return target.getCameraPoseTargetSpace().getPosition().z;
    }

    public boolean foundTarget() {
        return target != null;
    }

    // TODO: getter for time current target acquired

    // TODO: when replacing target, replace if newer, or if greater

    // TODO: what does this even semanticaly mean again lol in contrast to the other one
    public boolean resultValid() {
        return validResult != null;
    }

    public LLResult result() {
        return validResult;
    }
}
