package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "IntakeSubsystem")
public class IntakeSubsystem extends LinearOpMode {

    // ===== Subsystem (nested inside the TeleOp, outside runOpMode) =====
    static class intakeSubsystem {

        enum State {
            STOPPED,
            FORWARD,
            REVERSE
        }

        static final double INTAKE_POWER = 0.75;

        private final CRServo servo;
        private State state = State.STOPPED;
        private boolean previousButtonPressed;

        intakeSubsystem(HardwareMap hardwareMap, String servoName) {
            servo = hardwareMap.get(CRServo.class, servoName);
        }

        void update(boolean buttonPressed) {
            boolean justPressed = buttonPressed && !previousButtonPressed;
            previousButtonPressed = buttonPressed;

            if (justPressed) {
                if (state == State.STOPPED || state == State.REVERSE) {
                    state = State.FORWARD;
                } else {
                    state = State.REVERSE;
                }
            }

            if (state == State.FORWARD) {
                servo.setPower(INTAKE_POWER);
            } else if (state == State.REVERSE) {
                servo.setPower(-INTAKE_POWER);
            } else {
                servo.setPower(0.0);
            }
        }

        State getState() {
            return state;
        }

        void stop() {
            state = State.STOPPED;
            servo.setPower(0.0);
        }
    }

    // ===== OpMode =====
    @Override
    public void runOpMode() {
        // --- INIT stage ---
        intakeSubsystem intake = new intakeSubsystem(hardwareMap, "continuousServo");
        telemetry.addLine("Ready");
        telemetry.update();

        waitForStart();

        // --- RUN stage ---
        while (opModeIsActive()) {
            intake.update(gamepad1.a);
            telemetry.addData("Intake State", intake.getState());
            telemetry.update();
        }

        // --- STOP stage ---
        intake.stop();
    }
}