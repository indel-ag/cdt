################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
TAR_SRCS += \
../test.tar 

C_DEPS += \
./f1.d \
./f2.d 

OBJS += \
./f1.oprestripped \
./f2.oprestripped 

STRIPPEDOBJS += \
./f1.o \
./f2.o 

TEST30_1_GNU_SO_CJPEG_OUTPUT_OUTPUTS += \
./CDT.jpeg 

TEST30_1_GNU_SO_TAR_OUTPUTC_OUTPUTS += \
./f1.c \
./f2.c \
./CDT.bmp 


# Each subdirectory must supply rules for building sources it contributes
include f1.c.mk
include subdir.rule.mk

clean: clean--2e-

clean--2e-:
	-$(RM) ./CDT.bmp ./CDT.jpeg ./f1.c ./f1.d ./f1.o ./f1.oprestripped ./f2.c ./f2.d ./f2.o ./f2.oprestripped ./test_ar.h

.PHONY: clean--2e-

