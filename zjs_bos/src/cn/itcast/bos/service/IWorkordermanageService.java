package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Workordermanage;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public List<Workordermanage> findListNotStart();

	public Workordermanage findById(String id);

	public void start(String id);

	public void update(Workordermanage workordermanage);

}
