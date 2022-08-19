################################################################################
# Automatically-generated file. Do not edit!
################################################################################

%.obj: ../%.F90 subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test Fortran Compiler'
	myfort  -c -object:"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

MAIN.obj: AVE_CALCULATOR.mod ../MODULE.F90

./AVE_CALCULATOR.mod: MODULE.obj

