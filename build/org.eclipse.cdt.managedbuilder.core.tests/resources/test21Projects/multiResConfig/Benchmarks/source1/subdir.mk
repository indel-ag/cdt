################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../source1/Class1.cpp 

CPP_DEPS += \
./source1/Class1.d 

OBJS += \
./source1/Class1.o 


# Each subdirectory must supply rules for building sources it contributes
include source1/subdir.rule.mk

clean: clean-source1

clean-source1:
	-$(RM) ./source1/Class1.d ./source1/Class1.o

.PHONY: clean-source1

