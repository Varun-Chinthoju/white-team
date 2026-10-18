package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.LinearSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Intake Control")
public class IntakeTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, "intake");
        LinearSlideSubsystem slides = new LinearSlideSubsystem(hardwareMap, "slides");

        waitForStart();

        while (opModeIsActive()) {
            intake.update(gamepad1.a);
            slides.update(gamepad1.b);
            telemetry.addData("Intake", intake.getState());
            telemetry.addData("Slides", slides.getState());
            telemetry.update();
        }

        intake.stop();
        slides.stop();
    }
}
