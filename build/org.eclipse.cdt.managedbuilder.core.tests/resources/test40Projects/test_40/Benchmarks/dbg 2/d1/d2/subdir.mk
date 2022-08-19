################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../d1/d2/e.cpp \
../d1/d2/r.cpp 

CPP_DEPS += \
./d1/d2/e.d \
./d1/d2/r.d 

OBJS += \
./d1/d2/e.o \
./d1/d2/r.o 


# Each subdirectory must supply rules for building sources it contributes
include d1/d2/e.o.mk
include d1/d2/subdir.rule.mk

clean: clean-d1-2f-d2

clean-d1-2f-d2:
	-$(RM) ./d1/d2/e.d ./d1/d2/e.o ./d1/d2/r.d ./d1/d2/r.o

.PHONY: clean-d1-2f-d2

