package org.firstinspires.ftc.teamcode.Polymorphism;

//test test
// test test test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

// annotation used to differentiate TeleOp and Auto modes
// (i guess it just mainly changes the tab it appears in on the controller??)
@TeleOp
public class ZahaanCharlieOpMode extends LinearOpMode {
    // ** add private variables/robo parts later
    // placeholder robo parts for now

    // wheel parts
    // (F = Front, B = Back, L = Left, R = Right, W = Wheel)
    private DcMotor FLW;
    private DcMotor FRW;
    private DcMotor BLW;
    private DcMotor BRW;

    // arm parts
    private DcMotor VLS;
    private DcMotor HLS;
    private Servo outClaw;
    private Servo shoulder;
    private Servo elbow;
    private Servo inClaw;

    @Override
    public void runOpMode() {
        // ** set the robo parts to the corresponding "hardwareMap.get" values later
        // placeholder robo parts for now

        // wheel config
        FLW = hardwareMap.get(DcMotor.class, "FLW");
        FRW = hardwareMap.get(DcMotor.class, "FRW");
        BLW = hardwareMap.get(DcMotor.class, "BLW");
        BRW = hardwareMap.get(DcMotor.class, "BRW");

        // intake arm config
        HLS = hardwareMap.get(DcMotor.class,"HLS");
        shoulder = hardwareMap.get(Servo.class,"shoulder");
        elbow = hardwareMap.get(Servo.class,"elbow");
        inClaw = hardwareMap.get(Servo.class,"inClaw");

        //outtake arm config
        VLS = hardwareMap.get(DcMotor.class,"VLS");
        outClaw = hardwareMap.get(Servo.class,"outClaw");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // while opMode is running, allow the methods for manipulating the wheels and arms
            // to be used and print data values
            moveWheels(gamepad1);
            moveArm(gamepad1);

            telemetry.addData("FLW Motor Power", FLW.getPower());
            telemetry.addData("FRW Motor Power", FRW.getPower());
            telemetry.addData("BLW Motor Power", BLW.getPower());
            telemetry.addData("BRW Motor Power", BRW.getPower());

            telemetry.addData("Outtake Arm Motor Power", VLS.getPower());
            telemetry.addData("Intake Arm Motor Power", HLS.getPower());
            telemetry.addData("Outtake Claw Servo Position", outClaw.getPosition());
            telemetry.addData("Intake Claw Servo Position", inClaw.getPosition());
            telemetry.addData("Shoulder Servo Position", shoulder.getPosition());
            telemetry.addData("Elbow Servo Position", elbow.getPosition());

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
                FRW.setPower(drivePower);
                FLW.setPower(turnPower);
                BRW.setPower(drivePower);
                BLW.setPower(turnPower);
            } else if(strafePower  != 0){
                // ** recheck this
                //Drive and Strafe
                FLW.setPower(drivePower);
                FRW.setPower(-strafePower);
                BLW.setPower(-strafePower);
                BRW.setPower(drivePower);
            } else {
                // just driving
                FRW.setPower(drivePower);
                FLW.setPower(drivePower);
                BRW.setPower(drivePower);
                BLW.setPower(drivePower);
            }
        } else if(turnPower != 0){
            // just turning
            FLW.setPower(turnPower);
            FRW.setPower(-turnPower);
            BLW.setPower(turnPower);
            BRW.setPower(-turnPower);
        } else if(strafePower != 0){
            // just strafing
            FLW.setPower(strafePower);
            FRW.setPower(-strafePower);
            BLW.setPower(-strafePower);
            BRW.setPower(strafePower);
        } else {
            FRW.setPower(0);
            FLW.setPower(0);
            BRW.setPower(0);
            BLW.setPower(0);
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
                FRW.setPower(-strafePower);
                FLW.setPower(drivePower);
                BRW.setPower(drivePower);
                BLW.setPower(-strafePower);
                // just driving
            } if(drivePower != 0){
                FLW.setPower(drivePower);
                FRW.setPower(drivePower);
                BLW.setPower(drivePower);
                BRW.setPower(drivePower);
            }
        } else {
            FLW.setPower(0);
            FRW.setPower(0);
            BLW.setPower(0);
            BRW.setPower(0);
        }
        */
    }

    public void moveArm(Gamepad armpad){
        // ** the functions below are for the polymorphism bot **
        // presets servo positions
//        outClaw.setPosition(0);
//        elbow.setPosition(0);
//        shoulder.setPosition(0);

        // powers the robot's (arm) motors using the game pad 2 left joystick
        // (this will make the arm's motor power vary depending on how much you're using the joystick.
        // this can be changed later if we want the motor to have a constant power no matter what
        // value the joystick is giving)
        // linear slide(s) for outtake arm
        // (provides a threshold for deactivating motor power since joystick may not be at exactly 0)
        if(armpad.right_trigger > 0.25) {
            HLS.setPower(-armpad.right_trigger);
        } else if(armpad.left_trigger > 0.25){
            HLS.setPower(armpad.left_trigger);
        } else{
            HLS.setPower(0);
        }

        // same logic for intake and outtake linear slides
//        if(armpad.right_stick_y > 0.25 || armpad.right_stick_y < -0.25) {
//            HLS.setPower(-armpad.left_stick_y);
//        } else {
//            HLS.setPower(0);
//        }

        if(armpad.square) {
            shoulder.setPosition(-0.75);
            elbow.setPosition(0);
        } else if(armpad.triangle) {
            shoulder.setPosition(0);
            elbow.setPosition(1);
        } else if(armpad.circle){
            shoulder.setPosition(1);
            elbow.setPosition(1);
        }

        if (armpad.x) {
            if (inClaw.getPosition() < 0) {
                inClaw.setPosition(1);
            }else{
                inClaw.setPosition(-1);
            }
        }

        if(armpad.left_bumper && armpad.right_bumper){
            if (outClaw.getPosition() < 0) {
                outClaw.setPosition(1);
            }else{
                outClaw.setPosition(-1);
            }
        }
        else if(armpad.right_bumper){
            VLS.setPower(1);
        } else if(armpad.left_bumper){
            VLS.setPower(-1);
        } else{
            VLS.setPower(0);
        }
        // uses the gamepad's X button as a switch to tilt and un-tilt the outtake's "claw's" bucket
        // (1 = tilt, 0 = not tilt)
//        if(armpad.x) {
//            if(OutClaw.getPosition() < 0.5) {
//                OutClaw.setPosition(1);
//            } else {
//                OutClaw.setPosition(0);
//            }
//        }
//
//        // uses the gamepad's circle button as a switch to open and unopen the intake's claw
//        // (1 = open, 0 = closed)
//        if(armpad.circle) {
//            if(Wrist.getPosition() < 0.5) {
//                Wrist.setPosition(1);
//            } else {
//                Wrist.setPosition(0);
//            }
//        }
//
//        // pressing the triangle to trigger whatever actions will happen to hang/climb
//        if(armpad.triangle) {
//            // trigger hanging maneuver
//        }
    }
}
