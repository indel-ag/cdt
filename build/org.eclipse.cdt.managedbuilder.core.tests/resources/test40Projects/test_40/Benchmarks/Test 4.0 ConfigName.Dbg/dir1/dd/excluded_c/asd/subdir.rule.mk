################################################################################
# Automatically-generated file. Do not edit!
################################################################################

dir1/dd/excluded_c/asd/%.o: ../dir1/dd/excluded_c/asd/%.cpp dir1/dd/excluded_c/asd/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test 4.0 ToolName.compiler.gnu.cpp'
	g++ -Irel/path -I../proj/rel/path -I/abs/path -Ic:/abs/path -I"${WorkspaceDirPath}/test_40/dir1/dir2/dir3" -I"${WorkspaceDirPath}/test_40" -I"D:\docs\incs" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

