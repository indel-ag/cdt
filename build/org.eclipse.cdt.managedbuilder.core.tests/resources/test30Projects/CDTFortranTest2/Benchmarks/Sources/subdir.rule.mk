################################################################################
# Automatically-generated file. Do not edit!
################################################################################

Sources/%.obj: ../Sources/%.f90 Sources/subdir.rule.mk
	@echo 'Building file: $<'
	@echo 'Invoking: Test Fortran Compiler'
	myfort  -c -object:"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

Sources/a.obj: A2_MODULE.mod ../module/a2_module.f90 B1_MODULE.mod ../module/b1_module.f90 JJ_MODULE.mod ../module/jj_module.f90

Sources/ab.obj: B1_MODULE.mod ../module/b1_module.f90 e_MODULE.mod ../module/e_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/ac.obj: B1_MODULE.mod ../module/b1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 L_MODULE.mod ../module/l_module.f90

Sources/c.obj: A2_MODULE.mod ../module/a2_module.f90 B1_MODULE.mod ../module/b1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 XYZ_MODULE.mod ../module/xyz_module.f90

Sources/ca.obj: A2_MODULE.mod ../module/a2_module.f90 B1_MODULE.mod ../module/b1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90

Sources/cd.obj: A2_MODULE.mod ../module/a2_module.f90 B1_MODULE.mod ../module/b1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 JJ_MODULE.mod ../module/jj_module.f90 L_MODULE.mod ../module/l_module.f90

Sources/ce.obj: CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 e_MODULE.mod ../module/e_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90

Sources/d1.obj: A2_MODULE.mod ../module/a2_module.f90 CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 XYZ_MODULE.mod ../module/xyz_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/e.obj: B1_MODULE.mod ../module/b1_module.f90 C1_MODULE.mod ../module/c1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/et.obj: B1_MODULE.mod ../module/b1_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/f.obj: B2_MODULE.mod ../module/b2_module.f90 CBA_MODULE.mod ../module/cba_module.f90 CR_MODULE.mod ../module/cr_module.f90 JJ_MODULE.mod ../module/jj_module.f90 UN_MODULE.mod ../module/un_module.f90 XYZ_MODULE.mod ../module/xyz_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/fa.obj: A2_MODULE.mod ../module/a2_module.f90 L1_MODULE.mod ../module/l1_module.f90 M1_MODULE.mod ../module/m1_module.f90 UN_MODULE.mod ../module/un_module.f90 XYZ_MODULE.mod ../module/xyz_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/fex.obj: B1_MODULE.mod ../module/b1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 e_MODULE.mod ../module/e_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 P_MODULE.mod ../module/p_module.f90 V_MODULE.mod ../module/v_module.f90

Sources/ff.obj: M1_MODULE.mod ../module/m1_module.f90 P_MODULE.mod ../module/p_module.f90

Sources/g.obj: M1_MODULE.mod ../module/m1_module.f90 P_MODULE.mod ../module/p_module.f90

Sources/h.obj: B1_MODULE.mod ../module/b1_module.f90 C1_MODULE.mod ../module/c1_module.f90 CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/ha.obj: B1_MODULE.mod ../module/b1_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90

Sources/i.obj: B1_MODULE.mod ../module/b1_module.f90 e_MODULE.mod ../module/e_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/ia.obj: B1_MODULE.mod ../module/b1_module.f90 e_MODULE.mod ../module/e_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/ii.obj: C3_MODULE.mod ../module/c3_module.f90 CBA_MODULE.mod ../module/cba_module.f90 JJ_MODULE.mod ../module/jj_module.f90

Sources/is.obj: D.mod ../module/d.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/l.obj: B1_MODULE.mod ../module/b1_module.f90 B2_MODULE.mod ../module/b2_module.f90 CBA_MODULE.mod ../module/cba_module.f90 G_MOD.mod ../module/g_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 L_MODULE.mod ../module/l_module.f90

Sources/m.obj: UN_MODULE.mod ../module/un_module.f90 XXX_MODULE.mod ../module/xxx_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/ma.obj: JJ_MODULE.mod ../module/jj_module.f90 MG_MODULE.mod ../module/mg_module.f90 UN_MODULE.mod ../module/un_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/main.obj: F_MODULE.mod ../module/F_module.f90 JJ_MODULE.mod ../module/jj_module.f90

Sources/mo.obj: F_MODULE.mod ../module/F_module.f90 C3_MODULE.mod ../module/c3_module.f90 CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 JJ_MODULE.mod ../module/jj_module.f90 MG_MODULE.mod ../module/mg_module.f90 XXX_MODULE.mod ../module/xxx_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/o.obj: D.mod ../module/d.f90

Sources/p.obj: A_MODULE.mod ../module/a_module.f90 C2_MODULE.mod ../module/c2_module.f90 CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 JJ_MODULE.mod ../module/jj_module.f90 MF_MODULE.mod ../module/mf_module.f90 P_MODULE.mod ../module/p_module.f90

Sources/r.obj: M1_MODULE.mod ../module/m1_module.f90 P_MODULE.mod ../module/p_module.f90

Sources/rx.obj: D.mod ../module/d.f90 M1_MODULE.mod ../module/m1_module.f90 P_MODULE.mod ../module/p_module.f90

Sources/s.obj: B1_MODULE.mod ../module/b1_module.f90 K_MODULE.mod ../module/k_module.f90 M1_MODULE.mod ../module/m1_module.f90 UN_MODULE.mod ../module/un_module.f90

Sources/t.obj: A2_MODULE.mod ../module/a2_module.f90 B1_MODULE.mod ../module/b1_module.f90 L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/u.obj: D.mod ../module/d.f90 JJ_MODULE.mod ../module/jj_module.f90 MG_MODULE.mod ../module/mg_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/u1.obj: D.mod ../module/d.f90 JJ_MODULE.mod ../module/jj_module.f90 YYY_MODULE.mod ../module/yyy_module.f90

Sources/v.obj: L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/w.obj: B1_MODULE.mod ../module/b1_module.f90 JJ_MODULE.mod ../module/jj_module.f90 K_MODULE.mod ../module/k_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/x.obj: A1_MODULE.mod ../module/a1_module.f90 D.mod ../module/d.f90 JJ_MODULE.mod ../module/jj_module.f90 P_MODULE.mod ../module/p_module.f90 XXX_MODULE.mod ../module/xxx_module.f90

Sources/y.obj: D.mod ../module/d.f90 L_MODULE.mod ../module/l_module.f90 M1_MODULE.mod ../module/m1_module.f90 W_MODULE.mod ../module/w_module.f90

Sources/z.obj: A_MODULE.mod ../module/a_module.f90 CBA_MODULE.mod ../module/cba_module.f90 D.mod ../module/d.f90 JJ_MODULE.mod ../module/jj_module.f90 MF_MODULE.mod ../module/mf_module.f90 XXX_MODULE.mod ../module/xxx_module.f90

