package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogEncoder;
import frc.robot.Constants.motorConstants.*;

//The class is used to represent a drive talon and turn talon that are part of one the swerve modules
public class SwerveModule {
    //The Neo motors
    private CANSparkMax driveMotor;
    private CANSparkMax turnMotor;

    private RelativeEncoder driveRelative;

    //The encoder for the turn motor
    private AnalogEncoder turnEncoder;

    public SwerveModule(int driveMotorID, int turnMotorID, int turnEncoderID, boolean driveMotorInvert, boolean turnMotorInvert, double offsetAngle) {
        driveMotor = new CANSparkMax(driveMotorID, MotorType.kBrushless);
        turnMotor = new CANSparkMax(turnMotorID, MotorType.kBrushless);
        turnEncoder = new AnalogEncoder(turnEncoderID);

        turnEncoder.setPositionOffset(offsetAngle);

        driveMotor.setInverted(driveMotorInvert);
        turnMotor.setInverted(turnMotorInvert);
    }

    //Returns a SwerveModuleState representation of this SwerveModule
    public SwerveModuleState getState() {
        return new SwerveModuleState(this.getDriveVelocity(), new Rotation2d(this.getAbsoluteTurnPosition()));
    }

    public void set(double driveSet, double turnSet) {
        driveMotor.set(driveSet);
        turnMotor.set(turnSet);
    }

    //Metres position of the drive talon
    public double getDrivePosition() {
        return driveRelative.getPosition() * driveConstants.metresPerRotation;
    }

    //Radians position of the turn talon
    public double getAbsoluteTurnPosition() {
        return turnEncoder.getAbsolutePosition() * turnConstants.radsPerRotation;
    }

    //Metres/second velocity of the drive talon
    public double getDriveVelocity() {
        return driveRelative.getVelocity() * driveConstants.metresPerRotation;
    }
}
