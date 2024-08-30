package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

// annotation used to differentiate TeleOp and Auto modes
// (i guess it just mainly changes the tab it appears in on the controller??)
@TeleOp
public class DCOpMode extends LinearOpMode {
    // ** add private variables/robo parts later
    // placeholder robo parts for now
    // (F = Front, B = Back, L = Left, R = Right, W = Wheel)
    private DcMotor FLW;
    private DcMotor FRW;
    private DcMotor BLW;
    private DcMotor BRW;

    private DcMotor ArmL;
    private DcMotor ArmR;

    @Override
    public void runOpMode() {
        // ** set the robo parts to the corresponding "hardwareMap.get" values later
        // placeholder robo parts for now

        // wheel config
        FLW = hardwareMap.get(DcMotor.class, "FLW");
        FRW = hardwareMap.get(DcMotor.class, "FRW");
        BLW = hardwareMap.get(DcMotor.class, "BLW");
        BRW = hardwareMap.get(DcMotor.class, "BRW");

        // arm config
        ArmL = hardwareMap.get(DcMotor.class,"ArmL");
        ArmR = hardwareMap.get(DcMotor.class,"ArmR");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // while opMode is running, allow the methods for manipulating the wheels and arms
            // to be used and print data values
            moveWheels();
            moveArm();

            telemetry.addData("FLW Motor Power", FLW.getPower());
            telemetry.addData("FRW Motor Power", FRW.getPower());
            telemetry.addData("BLW Motor Power", BLW.getPower());
            telemetry.addData("BRW Motor Power", BRW.getPower());

            telemetry.addData("ArmL Motor Power", ArmL.getPower());
            telemetry.addData("ArmR Motor Power", ArmR.getPower());

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }

    public void moveWheels(){
        double drivePower = 0;
        double strafePower = 0;
        double turnPower = 0;

        // moves the robot's (wheel) motors forward and back using the game pad 1 left joystick
        drivePower = -this.gamepad1.left_stick_y;

        // moves the robot's (wheel) motors left and right using the game pad 1 left joystick
        strafePower = this.gamepad1.left_stick_x;

        // turns the robot's (wheel) motors left and right using the game pad 1 right joystick
        turnPower = this.gamepad1.right_stick_x;

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

    public void moveArm(){
        // powers the robot's (arm) motors using the game pad 2 left joystick
        // (this will make the arm's motor power vary depending on how much you're using the joystick.
        // this can be changed later if we want the motor to have a constant power no matter what
        // value the joystick is giving)
        if(this.gamepad2.left_stick_y != 0){
            ArmL.setPower(-this.gamepad2.left_stick_y);
            ArmR.setPower(-this.gamepad2.left_stick_y);
        }
    }
}
