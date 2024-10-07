package org.firstinspires.ftc.teamcode.Code_Blue;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

@Autonomous
public class MyFIRSTAutonomous extends LinearOpMode {

    static private DcMotor FLW;
    static private DcMotor BLW;
    static private DcMotor FRW;
    static private DcMotor BRW;

    @Override
    public void runOpMode() throws InterruptedException {
        FLW = hardwareMap.get(DcMotor.class, "FLW");
        BLW = hardwareMap.get(DcMotor.class, "BLW");
        BRW = hardwareMap.get(DcMotor.class, "BRW");
        FRW = hardwareMap.get(DcMotor.class, "FRW");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            powerWheels(2000, "forward");
            updatePhoneConsole();
        }
    }

    public static void powerWheels(int miliseconds, String direction) {
        new Thread(() -> {
            try {
                switch(direction){
                    case "forward":
                        FLW.setPower(.5);
                        BLW.setPower(.5);
                        FRW.setPower(-.5);
                        BRW.setPower(-.5);
                        Thread.sleep(miliseconds);
                        disablePower();
                        break;
                    case "backward":
                        FLW.setPower(-.5);
                        BLW.setPower(-.5);
                        FRW.setPower(.5);
                        BRW.setPower(.5);
                        Thread.sleep(miliseconds);
                        disablePower();
                        break;
                    case "right":
                        FLW.setPower(.5);
                        BLW.setPower(.5);
                        FRW.setPower(.5);
                        BRW.setPower(.5);
                        Thread.sleep(miliseconds);
                        disablePower();
                        break;
                    case "left":
                        FLW.setPower(-.5);
                        BLW.setPower(-.5);
                        FRW.setPower(-.5);
                        BRW.setPower(-.5);
                        Thread.sleep(miliseconds);
                        disablePower();
                }
            } catch (Exception e) {
            }
        }).start();
    }

    public static void disablePower() {
        FLW.setPower(0);
        BLW.setPower(0);
        FRW.setPower(0);
        BRW.setPower(0);
    }

    public void updatePhoneConsole() {
        telemetry.addData("FLW Power", FLW.getPower());
        telemetry.addData("BLW Power", BLW.getPower());
        telemetry.addData("FRW Power", FRW.getPower());
        telemetry.addData("BRW Power", BRW.getPower());
        telemetry.addData("Status", "Running");
        telemetry.update();
    }
}