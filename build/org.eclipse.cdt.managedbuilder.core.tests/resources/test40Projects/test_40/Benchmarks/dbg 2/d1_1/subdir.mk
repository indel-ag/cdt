################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../d1_1/i.cpp \
../d1_1/o.cpp 

CPP_DEPS += \
./d1_1/i.d \
./d1_1/o.d 

OBJS += \
./d1_1/i.o \
./d1_1/o.o 


# Each subdirectory must supply rules for building sources it contributes
include d1_1/subdir.rule.mk

clean: clean-d1_1

clean-d1_1:
	-$(RM) ./d1_1/i.d ./d1_1/i.o ./d1_1/o.d ./d1_1/o.o

.PHONY: clean-d1_1

