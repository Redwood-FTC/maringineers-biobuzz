package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.layout.Layout;

@TeleOp(name = "Launch Motor", group = "Test")
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/LaunchMotor.java
public class LaunchMotor extends OpMode {
    private DcMotorEx motor;
=======
public class launchmotor extends OpMode {
    private Hardware hardware;
>>>>>>> adeb5f8 (launch class):TeamCode/src/main/java/org/firstinspires/ftc/teamcode/launchmotor.java
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
