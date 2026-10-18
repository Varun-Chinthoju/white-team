package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class LinearSlideSubsystem {
    public static final double SLIDE_POWER = 1.0;
    public static final double PULLEY_RADIUS_INCHES = 0.75;
    public static final double MOUNT_HEIGHT_INCHES = 3.0;
    public static final double TARGET_HEIGHT_INCHES = 24.0;
    public static final int STAGE_COUNT = 4;

    public enum State {
        STOPPED,
        UP,
        DOWN
    }

    private final CRServo servo;
    private State state = State.STOPPED;
    private boolean previousButtonPressed;

    public LinearSlideSubsystem(HardwareMap hardwareMap, String servoName) {
        servo = hardwareMap.get(CRServo.class, servoName);
    }

    public void extend(double power) {
        servo.setPower(Range.clip(Math.abs(power), 0.0, 1.0));
    }

    public void retract(double power) {
        servo.setPower(-Range.clip(Math.abs(power), 0.0, 1.0));
    }

    public void update(boolean buttonPressed) {
        boolean justPressed = buttonPressed && !previousButtonPressed;
        previousButtonPressed = buttonPressed;

        if (justPressed) {
            state = state == State.STOPPED || state == State.DOWN ? State.UP : State.DOWN;
        }

        if (state == State.UP) {
            extend(SLIDE_POWER);
        } else if (state == State.DOWN) {
            retract(SLIDE_POWER);
        } else {
            stop();
        }
    }

    public State getState() {
        return state;
    }

    public void stop() {
        state = State.STOPPED;
        servo.setPower(0.0);
    }

    public static double pulleyRotationsForHeight(double targetHeightInches) {
        double liftDistance = Math.max(0.0, targetHeightInches - MOUNT_HEIGHT_INCHES);
        double stringTravel = liftDistance / STAGE_COUNT;
        double pulleyCircumference = 2.0 * Math.PI * PULLEY_RADIUS_INCHES;
        return stringTravel / pulleyCircumference;
    }

    public static double rotationsToTargetHeight() {
        return pulleyRotationsForHeight(TARGET_HEIGHT_INCHES);
    }
}
