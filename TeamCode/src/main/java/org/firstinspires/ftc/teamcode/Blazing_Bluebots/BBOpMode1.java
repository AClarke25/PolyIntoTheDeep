

package org.firstinspires.ftc.teamcode.Blazing_Bluebots;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class BBOpMode1 extends LinearOpMode {
    private DcMotor FLW;
    private DcMotor FRW;
    private DcMotor BLW;
    private DcMotor BRW;


    private DcMotor wormDL;
    private DcMotor RWD;
    private DcMotor LS;
    private Servo Claw;


    private int counter;




    @Override
    public void runOpMode() {
        FLW = hardwareMap.get(DcMotor.class, "FLW");
        FRW = hardwareMap.get(DcMotor.class, "FRW");
        BLW = hardwareMap.get(DcMotor.class, "BLW");
        BRW = hardwareMap.get(DcMotor.class, "BRW");


        wormDL = hardwareMap.get(DcMotor.class,"wormDL");
        wormDR = hardwareMap.get(DcMotor.class,"wormDR");
        //encoder
        wormDL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wormDL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //wormDR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        LS = hardwareMap.get(DcMotor.class,"LS");
        Claw = hardwareMap.get(Servo.class,"Claw");
        counter = 0;
        telemetry.addData("counter", counter);





        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();


        Claw.setPosition(0);


        //Bucket.setPosition(0.33);


        while (opModeIsActive()) {
            moveWheels(gamepad1);
            moveArm(gamepad2);


            //encoder
            double Lposition = wormDL.getCurrentPosition();
            //int Rposition = wormDL.getCurrentPosition();


            double CPR = 10766;
            double revolutions = Lposition/CPR;




            double angle = revolutions * 360;
            double angleNormalized = angle % 360;
            // Show the position of the motor on telemetry
            telemetry.addData("Encoder Position", Lposition);


            telemetry.addData("Encoder Revolutions", revolutions);
            telemetry.addData("Encoder Angle (Degrees)", angle);
            telemetry.addData("Encoder Angle - Normalized (Degrees)", angleNormalized);


            telemetry.addData("FLW Motor Power", FLW.getPower());
            telemetry.addData("FRW Motor Power", FRW.getPower());
            telemetry.addData("BLW Motor Power", BLW.getPower());
            telemetry.addData("BRW Motor Power", BRW.getPower());


            telemetry.addData("Linear Slide Power", LS.getPower());
            telemetry.addData("LS Position", LS.getCurrentPosition());


            telemetry.addData("Outtake Claw Servo Position", Claw.getPosition());


            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }


    public void moveWheels(Gamepad movepad){




        //right wheels are backward
        // moves the robot's (wheel) motors forward and back using the game pad 1 left joystick
        boolean driveFor = movepad.dpad_up;
        boolean driveBack = movepad.dpad_down;
        int driveF10 = driveFor ? 1 : 0;
        int driveB10 = driveBack ? 1 : 0;
        double drivePower = driveF10-driveB10;
        telemetry.addData("driveFor", driveFor);
        telemetry.addData("driveBack", driveBack);


        // moves the robot's (wheel) motors left and right using the game pad 1 left joystick
        boolean strafeLeft= movepad.dpad_left;
        boolean strafeRight= movepad.dpad_right;
        int strafeL10 = strafeLeft ? 1 : 0;
        int strafeR10 = strafeRight ? 1 : 0;
        double strafePower = strafeL10-strafeR10;
        telemetry.addData("strafeRight", strafeRight);
        telemetry.addData("strafeLeft", strafeLeft);
        // turns the robot's (wheel) motors left and right using the game pad 1 right joystick
        double turnPower = movepad.right_stick_x;
        telemetry.addData("turnPower", turnPower);

        if(movepad.x){
            drivePower*=0.5;
            turnPower*=0.5;
            strafePower*=0.5;
        }

        telemetry.addData("drivePower", drivePower);
        telemetry.addData("strafePower", strafePower);
        telemetry.addData("turnPower", turnPower);


        if(movepad.x){
            telemetry.addData("wendy", movepad.x);
            drivePower*=0.5;
            turnPower*=0.5;
            strafePower+=0.5;
        }

     boolean one = true;
        if(drivePower != 0) {


            if(counter > 5){
                one = false;
            }


            if(one) {
                counter++;
            }
            FRW.setPower(drivePower * 0.2*counter);
            FLW.setPower(-drivePower * 0.2*counter);
            BRW.setPower(drivePower * 0.2*counter);
            BLW.setPower(-drivePower * 0.2*counter);


        } else if(strafePower != 0){
            if(counter > 5){
                one = false;
            }
            if(one) {
                counter++;
            }


            FRW.setPower(-strafePower * 0.2*counter);
            FLW.setPower(-strafePower * 0.2*counter);
            BRW.setPower(strafePower * 0.2*counter);
            BLW.setPower(strafePower * 0.2*counter);


        } else if(turnPower != 0) {
            if(counter > 5){
                one = false;
            }
            if(one) {
                counter++;
            }


            FRW.setPower(turnPower);
            FLW.setPower(turnPower);
            BRW.setPower(turnPower);
            BLW.setPower(turnPower);
        }else {
            counter =0;
            FRW.setPower(0);
            FLW.setPower(0);
            BRW.setPower(0);
            BLW.setPower(0);
        }


    }






    public void moveArm(Gamepad armpad){

        //95d
        int wdUpPosition = 2841;
        //5degree
        int wdDownPosition = 149;

        boolean wdUp=false;
        wormDL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wormDR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(Math.abs(armpad.left_stick_y)> 0.25) {
            LS.setPower(-armpad.left_stick_y);
        } else {
            LS.setPower(0);
        }


        if(armpad.circle){
            if(!wdUp){
                //test wormdrive (how much power give 90 degree --> assign that much power
                wormDL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                wormDL.setTargetPosition(wdUpPosition);
                wormDR.setTargetPosition(wdUpPosition);

                wdUp = true;
            }else{
                wormDL.setTargetPosition(wdDownPosition);
                wormDR.setTargetPosition(wdDownPosition);
                wdUp = false;
            }
        }
       /*
       if(armpad.left_stick_y > 0.25 || armpad.left_stick_y < -0.25) {
           VLS.setPower(-armpad.left_stick_y);
       } else {
           VLS.setPower(0);
       }


       if(armpad.right_stick_y > 0.25 || armpad.right_stick_y < -0.25) {
           HLS.setPower(-armpad.right_stick_y);
       } else {
           HLS.setPower(0);
       }


       if(armpad.right_trigger>0.5) {
           if(Claw.getPosition()>0) {
               Claw.setPosition(Claw.getPosition() - 0.01);
           }
       }


       if(armpad.right_bumper) {
           if(Claw.getPosition()<0.5) {
               Claw.setPosition(Claw.getPosition() + 0.01);
           }
       }


       //writs
       if(armpad.left_trigger>0.5) {
           if(Wrist.getPosition()>0) {
               Wrist.setPosition(Wrist.getPosition() - 0.01);
           }
       }


       if(armpad.left_bumper) {
           if(Wrist.getPosition()<1) {
               Wrist.setPosition(Wrist.getPosition() + 0.01);
           }
       }


       if(armpad.x) {
           Bucket.setPosition(0.1);
       }


       if(Bucket.getPosition()<0.33) {
           Bucket.setPosition(Bucket.getPosition() + 0.001);
       }
       */


    }
}

