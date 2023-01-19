package com.mpxds.basic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpTabelaInterna;
import com.mpxds.basic.model.enums.MpTipoTabelaInterna;

public interface MpTabelaInternaRepository extends JpaRepository<MpTabelaInterna, Long> {
	//	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM MpTabelaInterna obj WHERE obj.mpTipoTabelaInterna = :mpTipoTabelaInterna" + 
												" AND obj.codigo = :codigo")
	public Optional<MpTabelaInterna> findTabelaInternasAndCodigo(
											@Param("mpTipoTabelaInterna") MpTipoTabelaInterna mpTipoTabelaInterna,
											@Param("codigo") String codigo);

	Optional<MpTabelaInterna> findByMpTipoTabelaInternaAndCodigo(MpTipoTabelaInterna mpTipoTabelaInterna,
																	String codigo);
	
	Optional<MpTabelaInterna> findByMpTipoTabelaInternaAndDescricao(MpTipoTabelaInterna mpTipoTabelaInterna,
																	String descricao);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM MpTabelaInterna obj ORDER BY obj.mpTipoTabelaInterna, obj.codigo")
	public List<MpTabelaInterna> findAllMpTipoTabelaInterna();
		
	@Transactional(readOnly=true)
	public List<MpTabelaInterna> findByMpTipoTabelaInternaOrderByCodigo(MpTipoTabelaInterna mpTipoTabelaInterna);
	
}
