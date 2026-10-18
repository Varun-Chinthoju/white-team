package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {
    public static final double INTAKE_POWER = 0.75;

    public enum State {
        STOPPED,
        FORWARD,
        REVERSE
    }

    private final CRServo servo;
    private State state = State.STOPPED;
    private boolean previousButtonPressed;

    public IntakeSubsystem(HardwareMap hardwareMap, String servoName) {
        servo = hardwareMap.get(CRServo.class, servoName);
    }

    public void update(boolean buttonPressed) {
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

    public State getState() {
        return state;
    }

    public void stop() {
        state = State.STOPPED;
        servo.setPower(0.0);
    }
}
