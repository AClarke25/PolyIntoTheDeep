package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

@TeleOp
public class MyFIRSTOpMode extends LinearOpMode {

    double tgtPower = 0;
    private DcMotor FLW;
    private DcMotor BLW;
    private DcMotor FRW;
    private DcMotor BRW;



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
            telemetry.addData("Status", "Running");
            telemetry.update();
            tgtPower = this.gamepad1.left_stick_y;
            FLW.setPower(-tgtPower);
            BLW.setPower(-tgtPower);
            FRW.setPower(tgtPower);
            BRW.setPower(tgtPower);
            telemetry.addData("Target Power", tgtPower);
            telemetry.addData("FLW Power", FLW.getPower());
            telemetry.addData("BLW Power", BLW.getPower());
            telemetry.addData("FRW Power", FRW.getPower());
            telemetry.addData("BRW Power", BRW.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}