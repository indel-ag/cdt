source2/source21/Class21.o: ../source2/source21/Class21.cpp source2/source21/Class21.o.mk
	@echo 'Building file: $<'
	@echo 'Invoking: compiler.gnu.cpp'
	g++ -DRESSPEC -I../headers -O0 -g3 -Wall -c -o "$@" "$<" && \
	echo -n '$(@:%.o=%.d)' $(dir $@) > '$(@:%.o=%.d)' && \
	g++ -MM -MG -P -w -DRESSPEC -I../headers -O0 -g3 -Wall -c   "$<" >> '$(@:%.o=%.d)'
	@echo 'Finished building: $<'
	@echo ' '

