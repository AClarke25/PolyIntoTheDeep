package org.firstinspires.ftc.teamcode.Polymorphism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

// annotation used to differentiate TeleOp and Auto modes
// (i guess it just mainly changes the tab it appears in on the controller??)

@TeleOp
public class DCOpMode extends LinearOpMode {
    // ** add private variables/robot parts later
    // placeholder robot parts for now

    // wheel parts labelled VERY CLEARLY
    private DcMotor LeftFrontWheel;
    private DcMotor RightFrontWheel;
    private DcMotor LeftBackWheel;
    private DcMotor RightBackWheel;

    // arm parts
    private DcMotor VertLSlide;
    private DcMotor HorzLSlide;
    private DcMotor IntakeArm;
    private Servo ClawFlip;
    private Servo ClawRotate;
    private Servo ClawOpen;

    @Override
    public void runOpMode() {
        // ** set the robot parts to the corresponding "hardwareMap.get" values later
        // placeholder robot parts for now

        // wheel config
        LeftFrontWheel = hardwareMap.get(DcMotor.class, "LeftFrontWheel");
        RightFrontWheel = hardwareMap.get(DcMotor.class, "RightFrontWheel");
        LeftBackWheel = hardwareMap.get(DcMotor.class, "LeftBackWheel");
        RightBackWheel = hardwareMap.get(DcMotor.class, "RightBackWheel");

        // arm config
        VertLSlide = hardwareMap.get(DcMotor.class,"VertLSlide");
        IntakeArm = hardwareMap.get(DcMotor.class,"IntakeArm");
        OutClaw = hardwareMap.get(Servo.class,"OutClaw");
        Wrist = hardwareMap.get(Servo.class,"Wrist");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // while opMode is running, allow the methods for manipulating the wheels and arms
            // to be used and print data values
            moveWheels(gamepad1);
            moveArm(gamepad2);

            telemetry.addData("LeftFrontWheel Motor Power", LeftFrontWheel.getPower());
            telemetry.addData("RightFrontWheel Motor Power", RightFrontWheel.getPower());
            telemetry.addData("LeftBackWheel Motor Power", LeftBackWheel.getPower());
            telemetry.addData("RightBackWheel Motor Power", RightBackWheel.getPower());

            telemetry.addData("Outtake Arm Motor Power", VertLSlide.getPower());
            telemetry.addData("Intake Arm Motor Power", IntakeArm.getPower());
            telemetry.addData("Outtake Claw Servo Position", OutClaw.getPosition());
            telemetry.addData("Intake Claw Servo Position", Wrist.getPosition());

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }

    public void moveWheels(Gamepad movepad){
        // ** the functions below could be for any bot, depending on what controls you want to use to drive the bot **
        double drivePower = 0;
        double strafePower = 0;
        double turnPower = 0;

        // moves the robot's (wheel) motors forward and back using the game pad 1 left joystick
        drivePower = -movepad.left_stick_y;

        // moves the robot's (wheel) motors left and right using the game pad 1 left joystick
        strafePower = movepad.left_stick_x;

        // turns the robot's (wheel) motors left and right using the game pad 1 right joystick
        turnPower = movepad.right_stick_x;

        // power supply for driving, strafing and turning (sometimes simultaneously)
        // im not exactly sure if this will work, i just copied it from last year's code. so yeah
        // ** recheck this later
        if(drivePower != 0){
            if(turnPower != 0){
                // ** recheck this
                //Drive and Turn
                RightFrontWheel.setPower(drivePower);
                LeftFrontWheel.setPower(turnPower);
                RightBackWheel.setPower(drivePower);
                LeftBackWheel.setPower(turnPower);
            } else if(strafePower  != 0){
                // ** recheck this
                //Drive and Strafe
                LeftFrontWheel.setPower(drivePower);
                RightFrontWheel.setPower(-strafePower);
                LeftBackWheel.setPower(-strafePower);
                RightBackWheel.setPower(drivePower);
            } else {
                // just driving
                RightFrontWheel.setPower(drivePower);
                LeftFrontWheel.setPower(drivePower);
                RightBackWheel.setPower(drivePower);
                LeftBackWheel.setPower(drivePower);
            }
        } else if(turnPower != 0){
            // just turning
            LeftFrontWheel.setPower(turnPower);
            RightFrontWheel.setPower(-turnPower);
            LeftBackWheel.setPower(turnPower);
            RightBackWheel.setPower(-turnPower);
        } else if(strafePower != 0){
            // just strafing
            LeftFrontWheel.setPower(strafePower);
            RightFrontWheel.setPower(-strafePower);
            LeftBackWheel.setPower(-strafePower);
            RightBackWheel.setPower(strafePower);
        } else {
            RightFrontWheel.setPower(0);
            LeftFrontWheel.setPower(0);
            RightBackWheel.setPower(0);
            LeftBackWheel.setPower(0);
        }


        /* vv things i tried but couldn't figure out vv

        boolean motorsPowered = false;

        // basically, if none of the game pad 1 joysticks are being used, this means the robot's
        // wheels shouldn't be moving, meaning those motors should return to a power of 0
        if(drivePower == 0 && strafePower == 0 && turnPower == 0){
            motorsPowered = false;
        } else {
            motorsPowered = true;
        }

        // gives different power values to the motors (depending on the action being done) if the
        // joysticks are being used
        if(motorsPowered){
            // driving and strafing
            if(drivePower != 0 && strafePower != 0){
                // im not exactly sure if this will work, i just copied it from last year's code
                RightFrontWheel.setPower(-strafePower);
                LeftFrontWheel.setPower(drivePower);
                RightBackWheel.setPower(drivePower);
                LeftBackWheel.setPower(-strafePower);
                // just driving
            } if(drivePower != 0){
                LeftFrontWheel.setPower(drivePower);
                RightFrontWheel.setPower(drivePower);
                LeftBackWheel.setPower(drivePower);
                RightBackWheel.setPower(drivePower);
            }
        } else {
            LeftFrontWheel.setPower(0);
            RightFrontWheel.setPower(0);
            LeftBackWheel.setPower(0);
            RightBackWheel.setPower(0);
        }
        */
    }

    public void moveArm(Gamepad armpad){
        // ** the functions below are for the polymorphism bot **
        // presets servo positions
        OutClaw.setPosition(0);
        Wrist.setPosition(0);

        // powers the robot's (arm) motors using the game pad 2 left joystick
        // (this will make the arm's motor power vary depending on how much you're using the joystick.
        // this can be changed later if we want the motor to have a constant power no matter what
        // value the joystick is giving)
        // linear slide(s) for outtake arm
        // (provides a threshold for deactivating motor power since joystick may not be at exactly 0)
        if(armpad.left_stick_y > 0.25 || armpad.left_stick_y < -0.25) {
            VertLSlide.setPower(-armpad.left_stick_y);
        } else {
            VertLSlide.setPower(0);
        }

        // same logic for intake and outtake linear slides
        if(armpad.right_stick_y > 0.25 || armpad.right_stick_y < -0.25) {
            IntakeArm.setPower(-armpad.left_stick_y);
        } else {
            IntakeArm.setPower(0);
        }

        // uses the gamepad's X button as a switch to tilt and un-tilt the outtake's "claw's" bucket
        // (1 = tilt, 0 = not tilt)
        if(armpad.x) {
            if(OutClaw.getPosition() < 0.5) {
                OutClaw.setPosition(1);
            } else {
                OutClaw.setPosition(0);
            }
        }

        // uses the gamepad's circle button as a switch to open and unopen the intake's claw
        // (1 = open, 0 = closed)
        if(armpad.circle) {
            if(Wrist.getPosition() < 0.5) {
                Wrist.setPosition(1);
            } else {
                Wrist.setPosition(0);
            }
        }

        // pressing the triangle to trigger whatever actions will happen to hang/climb
        if(armpad.triangle) {
            // trigger hanging maneuver
        }
    }
}