package org.waddys.xcloud.res.po.dao.repository.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.res.po.entity.vdc.ConfigInfoE;

public interface ConfiginfoRepository extends CrudRepository<ConfigInfoE, Long> {
    Page<ConfigInfoE> findAll(Pageable pageable);

    @Override
    List<ConfigInfoE> findAll();

    ConfigInfoE findByConfigId(String configId);


}
