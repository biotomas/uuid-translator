package de.herglotz.uuid.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.herglotz.uuid.jni.KeyEvent;

public class Settings {

	private static final String FILE = "settings.properties";

	private static final Logger LOG = LoggerFactory.getLogger(Settings.class);

	private static final String UUID_SEARCH_HOTKEY = "Search_Hotkey";
	private static final String NAME_SEARCH_HOTKEY = "Name_Search_Hotkey";
	private static final String UUID_REPLACE_HOTKEY = "Replace_Hotkey";
	private static final String NAME_REPLACE_HOTKEY = "Name_Replace_Hotkey";
	private static final String SHOW_TYPE = "Show_Type";
	private static final String LAST_WORKSPACE = "Last_Workspace";

	private Properties properties;

	public Settings() {
		properties = getDefaultProperties();
		try (InputStream in = new FileInputStream(FILE)) {
			properties.load(in);
		} catch (IOException e) {
			LOG.error("Reading settings file failed. Using defaults");
		}
	}

	private Properties getDefaultProperties() {
		Properties properties = new Properties();
		properties.setProperty(UUID_SEARCH_HOTKEY, new KeyEvent(true, false, "C").toString());
		properties.setProperty(NAME_SEARCH_HOTKEY, new KeyEvent(true, true, "C").toString());
		properties.setProperty(UUID_REPLACE_HOTKEY, new KeyEvent(true, false, "R").toString());
		properties.setProperty(NAME_REPLACE_HOTKEY, new KeyEvent(true, true, "R").toString());
		properties.setProperty(SHOW_TYPE, "false");
		return properties;
	}

	public KeyEvent getUUIDSearchHotkey() {
		return KeyEvent.fromString(properties.getProperty(UUID_SEARCH_HOTKEY));
	}

	public KeyEvent getNameSearchHotkey() {
		return KeyEvent.fromString(properties.getProperty(NAME_SEARCH_HOTKEY));
	}

	public KeyEvent getUUIDReplaceHotkey() {
		return KeyEvent.fromString(properties.getProperty(UUID_REPLACE_HOTKEY));
	}

	public KeyEvent getNameReplaceHotkey() {
		return KeyEvent.fromString(properties.getProperty(NAME_REPLACE_HOTKEY));
	}

	public boolean isShowType() {
		return Boolean.parseBoolean(properties.getProperty(SHOW_TYPE));
	}

	public String getLastWorkspace() {
		return properties.getProperty(LAST_WORKSPACE);
	}

	public void setLastWorkspace(String lastWorkspace) {
		LOG.info("Setting last workspace to [{}]", lastWorkspace);
		properties.setProperty(LAST_WORKSPACE, lastWorkspace);
		save();
	}

	public void setUUIDSearchHotkey(KeyEvent key) {
		LOG.info("Setting uuid search hotkey to [{}]", key);
		properties.setProperty(UUID_SEARCH_HOTKEY, key.toString());
		save();
	}

	public void setNameSearchHotkey(KeyEvent key) {
		LOG.info("Setting name search hotkey to [{}]", key);
		properties.setProperty(NAME_SEARCH_HOTKEY, key.toString());
		save();
	}

	public void setUUIDReplaceHotkey(KeyEvent key) {
		LOG.info("Setting uuid replace hotkey to [{}]", key);
		properties.setProperty(UUID_REPLACE_HOTKEY, key.toString());
		save();
	}

	public void setNameReplaceHotkey(KeyEvent key) {
		LOG.info("Setting name replace hotkey to [{}]", key);
		properties.setProperty(NAME_REPLACE_HOTKEY, key.toString());
		save();
	}

	public void setShowType(boolean showType) {
		LOG.info("Setting show type to [{}]", showType);
		properties.setProperty(SHOW_TYPE, String.valueOf(showType));
		save();
	}

	private void save() {
		try (OutputStream out = new FileOutputStream(FILE)) {
			properties.store(out, "");
		} catch (IOException e) {
			LOG.error("Failed to save settings");
		}
	}

}
