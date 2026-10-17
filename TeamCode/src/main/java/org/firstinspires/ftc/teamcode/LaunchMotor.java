package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.layout.Layout;

@TeleOp(name = "Launch Motor", group = "Test")
public class LaunchMotor extends OpMode {
    private Hardware hardware;
    private Launch launch;

    @Override
    public void init() {
        hardware = new Hardware(this);
        launch = new Launch(hardware.launchMotor, new Layout(this));
        launch.stop();
        telemetry.addLine("Hold gamepad 1 right bumper to launch at 50% velocity.");
    }

    @Override
    public void loop() {
        launch.runGamepad();
        launch.tel(telemetry);
    }

    @Override
    public void stop() {
        if (launch != null) {
            launch.stop();
        }
    }
}

