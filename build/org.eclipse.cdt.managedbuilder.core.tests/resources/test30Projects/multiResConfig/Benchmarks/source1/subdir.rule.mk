################################################################################
# Automatically-generated file. Do not edit!
################################################################################

source1/%.o: ../source1/%.cpp source1/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: MBS30.compiler.gnu.cpp'
	g++ -I../headers -O0 -g3 -Wall -c -fmessage-length=0 -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	g++ -MM -MG -P -w -I../headers -O0 -g3 -Wall -c -fmessage-length=0   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

