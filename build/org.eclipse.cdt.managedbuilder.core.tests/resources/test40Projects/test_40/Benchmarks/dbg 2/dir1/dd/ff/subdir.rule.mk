################################################################################
# Automatically-generated file. Do not edit!
################################################################################

dir1/dd/ff/%.o: ../dir1/dd/ff/%.c dir1/dd/ff/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test 4.0 ToolName.compiler.gnu.c'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

dir1/dd/ff/%.o: ../dir1/dd/ff/%.cpp dir1/dd/ff/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test 4.0 ToolName.compiler.gnu.cpp'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

