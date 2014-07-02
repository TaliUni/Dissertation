package org.jfm.event;

public class HelpURLChangeEvent extends BroadcastEvent {

	private String url;
	
	public HelpURLChangeEvent(String url)
	{
		setUrl(url);
	}
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return BroadcastEvent.CHANGE_HELP_URL;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
