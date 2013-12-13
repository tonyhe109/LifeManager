package com.lifemanager;

public class LifeManagerModule {
	private int _module_id = -1;
	private String _module_name = "NULL";

	public LifeManagerModule(int id, String name) {
		_module_id = id;
		_module_name = name;
	}

	public int getModuleID() {
		return _module_id;
	}

	public String getModuleName() {
		return _module_name;
	}

	public String toString() {
		return _module_name;
	}

}
