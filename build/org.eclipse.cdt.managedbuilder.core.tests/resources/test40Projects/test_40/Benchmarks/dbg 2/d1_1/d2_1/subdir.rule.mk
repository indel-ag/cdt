################################################################################
# Automatically-generated file. Do not edit!
################################################################################

d1_1/d2_1/%.o: ../d1_1/d2_1/%.cpp d1_1/d2_1/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test 4.0 ToolName.compiler.gnu.cpp'
	g++ -Id2_1_rel/path -I/d2_1_abs/path -Ic:/d2_1_abs/path -Id1_1_rel/path -I/d1_1_abs/path -Ic:/d1_1_abs/path -I"D:\d1_1_docs\incs" -I"D:\d2_1_docs\incs" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '
