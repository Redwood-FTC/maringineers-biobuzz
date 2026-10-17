package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Launch {
    private final DcMotorEx motor;
    private final Layout layout;
    private final double speed;
    private double targetSpeed;

    public Launch(DcMotorEx motor, Layout layout) {
        this.motor = motor;
        this.layout = layout;
        speed = 0.5 * motor.getMotorType().getMaxRPM()
                * motor.getMotorType().getTicksPerRev() / 60.0;
    }

    public void runGamepad() {
        if (layout.launch()) {
            targetSpeed = speed;
        } else {
            targetSpeed = 0;
        }
        motor.setVelocity(targetSpeed);
    }

    public void stop() {
        targetSpeed = 0;
        motor.setVelocity(0);
        motor.setPower(0);
    }

    public void tel(Telemetry telemetry) {
        telemetry.addData("Launch target (ticks/s)", targetSpeed);
        telemetry.addData("Launch actual (ticks/s)", motor.getVelocity());
        telemetry.addData("Launch position (ticks)", motor.getCurrentPosition());
    }
}
