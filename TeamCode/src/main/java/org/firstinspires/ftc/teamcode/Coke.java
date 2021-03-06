
package org.firstinspires.ftc.robotcontroller.external.samples;

import android.graphics.Color;

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


@TeleOp(name="Coke Drive", group="OpMods")  // @Autonomous(...) is the other common choice
public class Coke extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
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
        String CurrentLight = "None";
        telemetry.addData("AtecStudios", "VictoryForPhil & VictoryForEric");
        telemetry.addData("Sensitivity", Sensitivity * 100+ "%");
        telemetry.addData("DPad Up Reset", DPad_Up_Reset + " Up Reset");
        telemetry.addData("Ultra", Ultra.getUltrasonicLevel() + "cm");
        telemetry.addData("Color", Cs.red() + " : " + Cs.green() + " : " + Cs.blue());
        leftMotor.setPower(gamepad1.left_stick_y* Sensitivity);
        rightMotor.setPower(gamepad1.right_stick_y * Sensitivity);

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
