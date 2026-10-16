package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.layout.Layout;

@TeleOp(name = "Launch Motor", group = "Test")
public class LaunchMotor extends OpMode {
    private DcMotorEx motor;
    private Launch launch;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "launchMotor");
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launch = new Launch(motor, new Layout(this));
        launch.stop();
        telemetry.addLine("Hold gamepad 1 right bumper to launch at 50% velocity.");
    }

    @Override
    public void loop() {
        launch.runGamepad();
        telemetry.addData("Launcher velocity (ticks/s)", motor.getVelocity());
        telemetry.addData("Launcher encoder position (ticks)", motor.getCurrentPosition());
    }

    @Override
    public void stop() {
        if (launch != null) {
            launch.stop();
        }
    }
}
