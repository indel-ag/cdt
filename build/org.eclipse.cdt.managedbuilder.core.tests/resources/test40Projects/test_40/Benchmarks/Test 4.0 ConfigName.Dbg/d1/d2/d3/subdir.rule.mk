################################################################################
# Automatically-generated file. Do not edit!
################################################################################

d1/d2/d3/%.o: ../d1/d2/d3/%.cpp d1/d2/d3/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test 4.0 ToolName.compiler.gnu.cpp'
	g++ -Id3_rel/path -I/d3_abs/path -Ic:/d3_abs/path -I"D:\d3_docs\incs" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

