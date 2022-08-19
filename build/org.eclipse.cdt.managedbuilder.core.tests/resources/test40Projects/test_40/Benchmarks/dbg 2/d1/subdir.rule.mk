################################################################################
# Automatically-generated file. Do not edit!
################################################################################

d1/%.o: ../d1/%.cpp d1/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test 4.0 ToolName.compiler.gnu.cpp'
	g++ -Id1_rel/path -I../d1_proj/rel/path -I/d1_abs/path -Ic:/d1_abs/path -I"D:\d1_docs\incs" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

