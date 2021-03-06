
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;


@TeleOp(name="VictoryOp - Driver", group="OpMods")  // @Autonomous(...) is the other common choice

public class VictoryOpDriver extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    String light = "None";
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    private DcMotor leftGrab = null;
    private DcMotor rightGrab = null;
    private DcMotor liftMotor = null;

    private float Sensitivity = 1.0f;

    private boolean DPad_Up_Reset = true;
    private boolean DPad_Down_Reset = true;


    private UltrasonicSensor Ultra = null;
    private ColorSensor Cs = null;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor  = hardwareMap.dcMotor.get("right motor");

        liftMotor = hardwareMap.dcMotor.get("lift motor");

        leftGrab = hardwareMap.dcMotor.get("left grab");
        rightGrab  = hardwareMap.dcMotor.get("right grab");


        Ultra = hardwareMap.ultrasonicSensor.get("ultra");
        Cs = hardwareMap.colorSensor.get("Cs");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        telemetry.addData("AtecStudios", "VictoryForPhil & VictoryForEric");
        telemetry.addData("Sensitivity", Sensitivity * 100+ "%");
        telemetry.addData("DPad Up Reset", DPad_Up_Reset + " Up Reset");
        telemetry.addData("Ultra", Ultra.getUltrasonicLevel() + "cm");
        telemetry.addData("Color", "R:" + Cs.red() + "B:" + Cs.blue() + "G:" + Cs.green());
        telemetry.addData("Color", light);


        leftMotor.setPower(gamepad1.left_stick_y * Sensitivity);
        rightMotor.setPower(gamepad1.right_stick_y * Sensitivity);

        if(gamepad1.a){
            liftMotor.setPower(1);
        }else{
            liftMotor.setPower(0);
        }

        if(gamepad1.b){
            liftMotor.setPower(-1);
        }else{
            liftMotor.setPower(0);
        }

        if(gamepad1.left_bumper){
            leftGrab.setPower(-1);
        }else{
            leftGrab.setPower(0);
        }

        if(gamepad1.right_bumper){
            rightGrab.setPower(-1);
        }else{
            rightGrab.setPower(0);
        }

        if(gamepad1.left_trigger > 0.1){
            leftGrab.setPower(gamepad1.left_trigger);
        }

        if(gamepad1.right_trigger > 0.1){
            rightGrab.setPower(gamepad1.right_trigger);
        }


        Cs.enableLed(false);

        if(Cs.red() > Cs.blue()  && Cs.green() < 200){
            light = "Red";
        }
        else if(Cs.red() < Cs.blue() && Cs.green() < 200){
            light = "Blue";
        }else{
            light = "None";
        }
        if(gamepad1.dpad_up && Sensitivity < 1.0f && DPad_Up_Reset){
            Sensitivity += 0.10;
            DPad_Up_Reset = false;
        }
        if(gamepad1.dpad_down && Sensitivity > 0.25 && DPad_Down_Reset){
            Sensitivity -= 0.10;
            DPad_Down_Reset = false;
        }

        if(!gamepad1.dpad_up && !DPad_Up_Reset){
            DPad_Up_Reset = true;
        }
        if(!gamepad1.dpad_down && !DPad_Down_Reset){
            DPad_Down_Reset = true;
        }
    }

    @Override
    public void stop() {
    }

}
