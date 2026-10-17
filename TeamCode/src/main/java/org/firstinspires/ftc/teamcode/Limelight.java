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
        int targetId = colour == Colour.RED ? 24 : 20;
        for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
            if (tag.getFiducialId() == targetId) {
                target = tag;
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
        }
    }

    public double angle_from_target() {
        return target.getTargetXDegrees();
    }

    public boolean foundTarget() {
        return target != null;
    }

    public boolean resultValid() {
        return validResult != null;
    }

    public LLResult result() {
        return validResult;
    }
}
