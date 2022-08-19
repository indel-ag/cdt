################################################################################
# Automatically-generated file. Do not edit!
################################################################################

%.o: ../%.cpp subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -O2 -g1 -pedantic -pedantic-errors -Wall -Werror -c -fmessage-length=0 -v -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

