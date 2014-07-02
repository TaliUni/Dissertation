package org.jfm.event;


public class ChangeFilesystemEvent extends BroadcastEvent {

	private String filesystem;
	
	public ChangeFilesystemEvent() {
		super();
	}

	public ChangeFilesystemEvent(Object source) {
		super(source);

	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return BroadcastEvent.CHANGE_FILESYSTEM_TYPE;
	}

	/**
	 * @return the filesystem
	 */
	public String getFilesystem() {
		return filesystem;
	}

	/**
	 * @param filesystem the filesystem to set
	 */
	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}

	
}
